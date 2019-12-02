package uk.offtopica.addressutil.monero;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import uk.offtopica.addressutil.AddressEncoder;

public class MoneroAddressEncoder implements AddressEncoder<MoneroAddress> {
    private final MoneroBase58Codec base58Codec;

    public MoneroAddressEncoder() {
        base58Codec = new MoneroBase58Codec();
    }

    @Override
    public String encode(MoneroAddress address) {
        byte[] paymentId = address.getPaymentId().orElse(new byte[0]);

        // part1 = network byte || public spend key || public view key || payment id
        byte[] part1 = new byte[1 + address.getPublicSpendKey().length + address.getPublicViewKey().length
                + paymentId.length];
        part1[0] = address.getNetworkByte();
        System.arraycopy(address.getPublicSpendKey(), 0, part1, 1, address.getPublicSpendKey().length);
        System.arraycopy(address.getPublicViewKey(), 0, part1, 1 + address.getPublicSpendKey().length,
                address.getPublicViewKey().length);
        System.arraycopy(paymentId, 0, part1, 1 + address.getPublicSpendKey().length
                + address.getPublicViewKey().length, paymentId.length);

        // checksum = first 4 bytes of keccak256(part1)
        Keccak.Digest256 keccak256 = new Keccak.Digest256();
        keccak256.update(part1);
        byte[] digest = keccak256.digest();
        byte[] checksum = new byte[4];
        System.arraycopy(digest, 0, checksum, 0, checksum.length);

        // part2 = part 1 || checksum
        byte[] part2 = new byte[part1.length + checksum.length];
        System.arraycopy(part1, 0, part2, 0, part1.length);
        System.arraycopy(checksum, 0, part2, part1.length, checksum.length);

        return base58Codec.encode(part2);
    }
}

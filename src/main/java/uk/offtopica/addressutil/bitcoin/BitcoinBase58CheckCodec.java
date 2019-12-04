package uk.offtopica.addressutil.bitcoin;

import org.bouncycastle.jcajce.provider.digest.SHA256;

import java.util.Arrays;

public class BitcoinBase58CheckCodec extends BitcoinBase58Codec {
    private static final int CHECKSUM_BYTES = 4;

    @Override
    public byte[] decode(String input) throws BitcoinBase58Exception {
        byte[] decoded = super.decode(input);

        if (decoded.length < CHECKSUM_BYTES) {
            throw new BitcoinBase58Exception("Input too short");
        }

        byte[] data = Arrays.copyOfRange(decoded, 0, decoded.length - CHECKSUM_BYTES);
        byte[] checksum = Arrays.copyOfRange(decoded, decoded.length - CHECKSUM_BYTES, decoded.length);

        SHA256.Digest sha256 = new SHA256.Digest();
        byte[] dataHash = sha256.digest(data);
        sha256.reset();
        dataHash = sha256.digest(dataHash);
        byte[] computedChecksum = Arrays.copyOfRange(dataHash, 0, CHECKSUM_BYTES);

        if (!Arrays.equals(checksum, computedChecksum)) {
            throw new BitcoinBase58Exception("Bad checksum");
        }

        return data;
    }

    @Override
    public String encode(byte[] data) {
        SHA256.Digest sha256 = new SHA256.Digest();
        byte[] dataHash = sha256.digest(data);
        sha256.reset();
        dataHash = sha256.digest(dataHash);
        byte[] checksum = Arrays.copyOfRange(dataHash, 0, CHECKSUM_BYTES);

        // return base58(data || checksum)
        byte[] concat = new byte[data.length + checksum.length];
        System.arraycopy(data, 0, concat, 0, data.length);
        System.arraycopy(checksum, 0, concat, data.length, checksum.length);

        return super.encode(concat);
    }
}

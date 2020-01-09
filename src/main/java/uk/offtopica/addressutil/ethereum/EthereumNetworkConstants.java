package uk.offtopica.addressutil.ethereum;

import java.util.Optional;

public class EthereumNetworkConstants {
    public static final EthereumNetworkConstants ETH_MAINNET = new EthereumNetworkConstants(false, 1);

    private final boolean adoptedEip1191;
    private final Integer chainId;

    public EthereumNetworkConstants(boolean adoptedEip1191, Integer chainId) {
        if (adoptedEip1191 && chainId == null) {
            throw new IllegalArgumentException("chainId must be not null if adoptedEip1191 is set");
        }

        this.adoptedEip1191 = adoptedEip1191;
        this.chainId = chainId;
    }

    public boolean hasAdoptedEip1191() {
        return adoptedEip1191;
    }

    public Optional<Integer> getChainId() {
        return Optional.ofNullable(chainId);
    }
}

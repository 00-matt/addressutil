package uk.offtopica.addressutil.ethereum;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import uk.offtopica.addressutil.InvalidAddressException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EthereumAddressDecoderTest {
    private final EthereumAddressDecoder ethMain;

    EthereumAddressDecoderTest() {
        ethMain = new EthereumAddressDecoder(EthereumNetworkConstants.ETH_MAINNET);
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-valid-eth-mainnet.csv", numLinesToSkip = 1)
    void testDecodeValid(String address) throws InvalidAddressException {
        assertNotNull(ethMain.decode(address));
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @CsvFileSource(resources = "address-invalid-eth-mainnet.csv", numLinesToSkip = 1)
    void testDecodeInvalid(String address) {
        assertThrows(InvalidAddressException.class, () -> ethMain.decode(address));
    }
}

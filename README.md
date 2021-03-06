# addressutil

Create, verify, and manipulate crypto-currency addresses.

## Supported crypto-currencies

* Bitcoin
* Ethereum
* Monero

## Example

    boolean isValidMoneroAddress(String address) {
        MoneroAddressDecoder decoder = new MoneroAddressDecoder();
        try {
            decoder.decode(address);
            return true;
        } catch (InvalidAddressException e) {
            return false;
        }
    }

## License

Released under the terms of the MIT license.
See [LICENSE](LICENSE) for more information.

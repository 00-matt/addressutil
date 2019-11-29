package uk.offtopica.addressutil;

public interface AddressDecoder<T extends Address> {
    T decode(String address) throws InvalidAddressException;
}

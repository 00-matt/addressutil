package uk.offtopica.addressutil;

public interface AddressEncoder<T extends Address> {
    String encode(T address);
}

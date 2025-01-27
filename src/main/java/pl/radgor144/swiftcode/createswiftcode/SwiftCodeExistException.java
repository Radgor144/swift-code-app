package pl.radgor144.swiftcode.createswiftcode;

public class SwiftCodeExistException extends RuntimeException {
    public SwiftCodeExistException(String swiftCode) {
        super("SwiftCode %s already exist!".formatted(swiftCode));
    }
}

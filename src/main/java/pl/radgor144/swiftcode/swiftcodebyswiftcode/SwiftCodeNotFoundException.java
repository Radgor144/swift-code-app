package pl.radgor144.swiftcode.swiftcodebyswiftcode;


public class SwiftCodeNotFoundException extends RuntimeException {

    public SwiftCodeNotFoundException(String swiftCode) {
        super("Not found swiftCode %s".formatted(swiftCode));
    }
}

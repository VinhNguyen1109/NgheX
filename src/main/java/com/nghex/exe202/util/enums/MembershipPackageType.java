package com.nghex.exe202.util.enums;

public enum MembershipPackageType {
    WEEK(1L),
    MONTH(2L),
    YEAR(3L);

    private final Long value;

    MembershipPackageType(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    // Optional: phương thức để lấy enum theo giá trị Long
    public static MembershipPackageType fromValue(Long value) {
        for (MembershipPackageType type : MembershipPackageType.values()) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid package type value: " + value);
    }
}

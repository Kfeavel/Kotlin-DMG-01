package gameboy.cpu

data class Flags(
    val zero: Boolean = false,
    val subtract: Boolean = false,
    val halfCarry: Boolean = false,
    val carry: Boolean = false,
) {
    companion object {
        const val ZERO_FLAG_BYTE_POSITION = 7
        const val SUBTRACT_FLAG_BYTE_POSITION = 6
        const val HALF_CARRY_FLAG_BYTE_POSITION = 5
        const val CARRY_FLAG_BYTE_POSITION = 4
    }

    fun toUByte(): UByte {
        return (
            ((if (zero) 1 else 0) shl ZERO_FLAG_BYTE_POSITION) or
            ((if (subtract) 1 else 0) shl SUBTRACT_FLAG_BYTE_POSITION) or
            ((if (halfCarry) 1 else 0) shl HALF_CARRY_FLAG_BYTE_POSITION) or
            ((if (carry) 1 else 0) shl CARRY_FLAG_BYTE_POSITION)
        ).toUByte()
    }

    constructor(byte: UByte) : this(
        zero = (((byte.toInt() ushr ZERO_FLAG_BYTE_POSITION) and 0b1) != 0),
        subtract = (((byte.toInt() ushr SUBTRACT_FLAG_BYTE_POSITION) and 0b1) != 0),
        halfCarry = (((byte.toInt() ushr HALF_CARRY_FLAG_BYTE_POSITION) and 0b1) != 0),
        carry = (((byte.toInt() ushr CARRY_FLAG_BYTE_POSITION) and 0b1) != 0),
    )
}

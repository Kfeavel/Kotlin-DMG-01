package gameboy.cpu.instructions.arithmetic

// https://stackoverflow.com/a/57822729
// https://gist.github.com/meganesu/9e228b6b587decc783aa9be34ae27841

fun isAdditionHalfCarry(sum: UByte, addend: UByte): Boolean {
    return (((sum and 0x0Fu) + (addend and 0x0Fu)) and 0x10u) == 0x10u
}

fun isAdditionHalfCarry(sum: UShort, addend: UShort): Boolean {
    return (((sum and 0x00FFu) + (addend and 0x00FFu)) and 0x100u) == 0x100u
}

fun isSubtractionHalfCarry(difference: UByte, term: UByte): Boolean {
    return (((difference and 0x0Fu).toByte() - (term and 0x0Fu).toByte()) < 0)
}

fun isSubtractionHalfCarry(difference: UShort, term: UShort): Boolean {
    return (((difference and 0x00FFu).toByte() - (term and 0x00FFu).toByte()) < 0)
}

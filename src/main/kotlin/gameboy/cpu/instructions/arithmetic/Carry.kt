package gameboy.cpu.instructions.arithmetic

// https://stackoverflow.com/a/57822729
fun isAdditionHalfCarry(sum: UByte, addend: UByte): Boolean {
    return (((sum and 0x0Fu) + (addend and 0x0Fu)) and 0x10u) == 0x10u
}

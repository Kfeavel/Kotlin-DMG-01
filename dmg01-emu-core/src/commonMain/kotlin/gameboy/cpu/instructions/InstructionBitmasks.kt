package gameboy.cpu.instructions

internal interface InstructionBitmasks {
    val mask: UByte
    val opcode: UByte
    // Register bits should always bits not masked by the opcode mask
    val register: UByte
        get() = mask.inv()
}

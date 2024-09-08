package gameboy.cpu.instructions

/**
 * [Instructions Reference #1](https://gbdev.io/pandocs/CPU_Instruction_Set.html#cpu-instruction-set)
 *
 * [Instructions Reference #2](https://gekkio.fi/files/gb-docs/gbctr.pdf)
 *
 * TODO: Should this go in the `instructions` or the `registers` package?
 */
interface InstructionTarget {
    val register: UByte
}

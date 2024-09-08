package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.ADDar8
import gameboy.cpu.instructions.arithmetic.INCr8
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import gameboy.memory.MemoryBus
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TestInstruction {
    private lateinit var registers: Registers
    private lateinit var bus: MemoryBus

    @BeforeTest
    fun `Reset State`() {
        registers = Registers()
        bus = MemoryBus()
    }


    @Test
    fun `Prefixed Opcode Decoding`() {
        // TODO: Add actual tests once prefixed instructions are added
        var didThrow = false
        try {
            Instruction.fromByte(
                opcode = 0xCBu,
                registers = registers,
                bus = bus,
            )
        } catch (e: IllegalStateException) {
            didThrow = true
        }

        assertEquals(true, didThrow)
    }

    @Test
    fun `Opcode Decoding`() {
        val nop = Instruction.fromByte(
            opcode = 0x00u,
            registers = registers,
            bus = bus,
        )
        assertTrue(nop is NOP)

        val halt = Instruction.fromByte(
            opcode = 0x76u,
            registers = registers,
            bus = bus,
        )
        assertTrue(halt is HALT)

        val illegal = Instruction.fromByte(
            opcode = 0xD3u,
            registers = registers,
            bus = bus,
        )
        assertTrue(illegal is HALT)

        val incr8a = Instruction.fromByte(
            opcode = 0x24u,
            registers = registers,
            bus = bus,
        )
        assertTrue(incr8a is INCr8)
        assertEquals(R8.H, incr8a.dest)

        val addr8a = Instruction.fromByte(
            opcode = 0x80u,
            registers = registers,
            bus = bus,
        )
        assertTrue(addr8a is ADDar8)
        assertEquals(R8.B, addr8a.dest)
    }
}
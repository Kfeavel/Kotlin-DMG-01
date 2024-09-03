package gameboy.cpu

import gameboy.cpu.exceptions.HaltException
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TestCPU {
    private lateinit var cpu: CPU

    @BeforeTest
    fun `Reset State`() {
        cpu = CPU()
    }

    @Test
    // FIXME: Break this up into multiple different tests
    fun `Test Step`() {
        val instructions = listOf<UByte>(
            0x3Cu, // INC A
            0x00u, // NOP
            0x3Cu, // INC A
            0x3Cu, // INC A
            0x04u, // INC B
            0x04u, // INC B
            0x80u, // ADD A, B
            0x76u, // HALT
        )

        cpu.bus.memory.addAll(0, instructions)

        assertThrows<HaltException> {
            repeat(instructions.size) {
                cpu.step()
            }
        }

        assertEquals(0x02u, cpu.registers.b)
        assertEquals(0x05u, cpu.registers.a)
        assertEquals(0x07u, cpu.registers.pc)
    }
}

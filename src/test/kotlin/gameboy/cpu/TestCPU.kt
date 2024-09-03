package gameboy.cpu

import gameboy.cpu.exceptions.HaltException
import gameboy.cpu.registers.Registers
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
    fun `Test Step - Arithmetic`() {
        // Series of no-ops to test arithmetic
        val instructions = listOf<UByte>(
            // Increment and Decrement No-op
            0x04u, // INC B
            0x05u, // DEC B
            // Addition & Subtraction No-op
            0x0Cu, // INC C
            0x81u, // ADD A, C
            0x91u, // SUB A, C
        )

        cpu.bus.memory.addAll(0, instructions)

        repeat(instructions.size) {
            cpu.step()
        }

        assertEquals(0x00u, cpu.registers.a)
        assertEquals(0x00u, cpu.registers.b)
        assertEquals(0x01u, cpu.registers.c)
        assertEquals(instructions.size.toUShort(), cpu.registers.pc)
    }

    @Test
    fun `Test Step - NOP & HALT`() {
        // Series of no-ops to test arithmetic
        val instructions = listOf<UByte>(
            0x00u, // NOP
            0x76u, // HALT
        )

        cpu.bus.memory.addAll(0, instructions)

        assertThrows<HaltException> {
            repeat(instructions.size) {
                cpu.step()
            }
        }

        // Only PC should have changed between NOP and HALT
        // NOP will increment PC but HALT will not, so we should have one less
        assertEquals(Registers(pc = (instructions.size - 1).toUShort()), cpu.registers)
    }
}

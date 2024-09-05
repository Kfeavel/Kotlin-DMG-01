package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.XORar8
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestXXORar8 {
    @Test
    fun `Test XOR`() {
        val registers = Registers(
            a = 0xA0u,
            b = 0x0Bu
        ).apply {
            XORar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0xABu, registers.a)
    }

    @Test
    fun `Test XOR (2)`() {
        val registers = Registers(
            a = 0xFFu,
            b = 0xABu
        ).apply {
            XORar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0x54u, registers.a)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            a = 0x0Bu,
            b = 0x0Bu
        ).apply {
            XORar8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Zero (Inverted)`() {
        val registers = Registers(
            a = 0xF0u,
            b = 0x0Fu
        ).apply {
            XORar8(registers = this, target = R8.B).execute()
        }
        assertEquals(false, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers().apply {
            XORar8(registers = this, target = R8.B).execute()
        }
        assertEquals(false, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers().apply {
            XORar8(registers = this, target = R8.B).execute()
        }
        assertEquals(false, registers.f.halfCarry)
    }

    @Test
    fun `Test Carry`() {
        val registers = Registers().apply {
            XORar8(registers = this, target = R8.C).execute()
        }
        assertEquals(false, registers.f.carry)
    }
}

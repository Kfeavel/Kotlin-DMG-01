package gameboy.cpu.instructions

import gameboy.cpu.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestADD {
    @Test
    fun `Test Add`() {
        val registers = Registers(
            a = 0xA0u,
            b = 0x0Bu
        ).apply {
            ADD(target = ArithmeticTarget.B).execute(this@apply)
        }
        assertEquals(0xABu, registers.a)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            ADD(target = ArithmeticTarget.B).execute(this@apply)
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Subtract`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            ADD(target = ArithmeticTarget.B).execute(this@apply)
        }
        assertEquals(false, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers(
            a = 0x0Fu,
            b = 0x0Fu
        ).apply {
            ADD(target = ArithmeticTarget.B).execute(this@apply)
        }
        assertEquals(true, registers.f.halfCarry)
    }

    @Test
    fun `Test Overflow`() {
        val registers = Registers(
            a = 0xFFu,
            c = 0x01u
        ).apply {
            ADD(target = ArithmeticTarget.C).execute(this@apply)
        }
        assertEquals(true, registers.f.carry)
    }
}

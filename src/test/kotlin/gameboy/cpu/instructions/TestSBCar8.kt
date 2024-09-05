package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.SBCar8
import gameboy.cpu.registers.Flags
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestSBCar8 {
    @Test
    fun `Test Sub`() {
        val registers = Registers(
            a = 0xA0u,
            b = 0x0Bu,
            f = Flags(
                carry = true
            )
        ).apply {
            SBCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0x94u, registers.a)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            SBCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Zero With Carry`() {
        val registers = Registers(
            a = 0x02u,
            b = 0x01u,
            f = Flags(
                carry = true
            )
        ).apply {
            SBCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            SBCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers(
            a = 0x10u,
            b = 0x00u,
            f = Flags(
                carry = true
            )
        ).apply {
            SBCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.halfCarry)
    }

    @Test
    fun `Test Overflow`() {
        val registers = Registers(
            a = 0x00u,
            c = 0x00u,
            f = Flags(
                carry = true
            )
        ).apply {
            SBCar8(registers = this, target = R8.C).execute()
        }
        assertEquals(true, registers.f.carry)
    }
}

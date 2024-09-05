package gameboy.cpu.instructions

import gameboy.cpu.instructions.arithmetic.ADCar8
import gameboy.cpu.registers.Flags
import gameboy.cpu.registers.R8
import gameboy.cpu.registers.Registers
import kotlin.test.Test
import kotlin.test.assertEquals

class TestADCar8 {
    @Test
    fun `Test Add`() {
        val registers = Registers(
            a = 0xA0u,
            b = 0x0Bu,
            f = Flags(
                carry = true
            )
        ).apply {
            ADCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(0xACu, registers.a)
    }

    @Test
    fun `Test Zero`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            ADCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.zero)
    }

    @Test
    fun `Test Zero With Carry`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u,
            f = Flags(
                carry = true
            )
        ).apply {
            ADCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(false, registers.f.zero)
    }

    @Test
    fun `Test Subtract Flag`() {
        val registers = Registers(
            a = 0x00u,
            b = 0x00u
        ).apply {
            ADCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(false, registers.f.subtract)
    }

    @Test
    fun `Test Half Carry`() {
        val registers = Registers(
            a = 0x0Fu,
            b = 0x0Eu,
            f = Flags(
                carry = true
            )
        ).apply {
            ADCar8(registers = this, target = R8.B).execute()
        }
        assertEquals(true, registers.f.halfCarry)
    }

    @Test
    fun `Test Overflow`() {
        val registers = Registers(
            a = 0xFFu,
            c = 0x00u,
            f = Flags(
                carry = true
            )
        ).apply {
            ADCar8(registers = this, target = R8.C).execute()
        }
        assertEquals(true, registers.f.carry)
    }
}

package gameboy.cpu.registers

import kotlin.test.Test
import kotlin.test.assertEquals

class TestRegisters {
    @Test
    fun `Registers uShort operations`() {
        assertEquals(
            expected = 0xAAF0u,
            actual = Registers(
                a = 0xAAu,
                f = Flags(
                    zero = true,
                    subtract = true,
                    halfCarry = true,
                    carry = true
                )
            ).af,
        )
        assertEquals(
            expected = 0xBBCCu,
            actual = Registers(
                b = 0xBBu,
                c = 0xCCu,
            ).bc,
        )
        assertEquals(
            expected = 0xDDEEu,
            actual = Registers(
                d = 0xDDu,
                e = 0xEEu,
            ).de,
        )
        assertEquals(
            expected = 0x8899u,
            actual = Registers(
                h = 0x88u,
                l = 0x99u,
            ).hl,
        )
    }
}

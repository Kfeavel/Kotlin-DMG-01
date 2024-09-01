package gameboy.cpu

import kotlin.test.Test
import kotlin.test.assertEquals

class TestFlags {
    @Test
    fun `Flags uByte constructor()`() {
        assertEquals(
            expected = Flags(0xF0u),
            actual = Flags(
                zero = true,
                subtract = true,
                halfCarry = true,
                carry = true,
            ),
        )
        assertEquals(
            expected = Flags(0x00u),
            actual = Flags(
                zero = false,
                subtract = false,
                halfCarry = false,
                carry = false,
            ),
        )
        assertEquals(
            expected = Flags(0x80u),
            actual = Flags(
                zero = true,
                subtract = false,
                halfCarry = false,
                carry = false,
            ),
        )
        assertEquals(
            expected = Flags(0x40u),
            actual = Flags(
                zero = false,
                subtract = true,
                halfCarry = false,
                carry = false,
            ),
        )
        assertEquals(
            expected = Flags(0x20u),
            actual = Flags(
                zero = false,
                subtract = false,
                halfCarry = true,
                carry = false,
            )
        )
        assertEquals(
            expected = Flags(0x10u),
            actual = Flags(
                zero = false,
                subtract = false,
                halfCarry = false,
                carry = true,
            ),
        )
    }

    @Test
    fun `Flags toUByte()`() {
        assertEquals(
            expected = 0xF0u,
            actual = Flags(
                zero = true,
                subtract = true,
                halfCarry = true,
                carry = true,
            ).toUByte()
        )
        assertEquals(
            expected = 0x00u,
            actual = Flags(
                zero = false,
                subtract = false,
                halfCarry = false,
                carry = false,
            ).toUByte()
        )
        assertEquals(
            expected = 0x80u,
            actual = Flags(
                zero = true,
                subtract = false,
                halfCarry = false,
                carry = false,
            ).toUByte()
        )
        assertEquals(
            expected = 0x40u,
            actual = Flags(
                zero = false,
                subtract = true,
                halfCarry = false,
                carry = false,
            ).toUByte()
        )
        assertEquals(
            expected = 0x20u,
            actual = Flags(
                zero = false,
                subtract = false,
                halfCarry = true,
                carry = false,
            ).toUByte()
        )
        assertEquals(
            expected = 0x10u,
            actual = Flags(
                zero = false,
                subtract = false,
                halfCarry = false,
                carry = true,
            ).toUByte()
        )
    }
}

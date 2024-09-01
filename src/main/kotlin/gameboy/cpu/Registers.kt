package gameboy.cpu

data class Registers(
    var a: UByte = 0u,
    var b: UByte = 0u,
    var c: UByte = 0u,
    var d: UByte = 0u,
    var e: UByte = 0u,
    var f: Flags = Flags(),
    var h: UByte = 0u,
    var l: UByte = 0u,
) {
    private fun readUShort(high: UByte, low: UByte): UShort {
        return ((high.toInt() shl 8).toUShort() or low.toUShort())
    }

    private fun writeUShort(value: UShort, high: (UByte) -> Unit, low: (UByte) -> Unit) {
        high((value.toInt() and 0xFF00 shr 8).toUByte())
        low((value.toInt() and 0x00FF).toUByte())
    }

    var af: UShort
        get() = readUShort(a, f.toUByte())
        set(value) = writeUShort(value, this::a::set, ::Flags)
    var bc: UShort
        get() = readUShort(b, c)
        set(value) = writeUShort(value, this::b::set, this::c::set)
    var de: UShort
        get() = readUShort(d, e)
        set(value) = writeUShort(value, this::d::set, this::e::set)
    var hl: UShort
        get() = readUShort(h, l)
        set(value) = writeUShort(value, this::h::set, this::l::set)
}

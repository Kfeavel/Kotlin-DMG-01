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
    private fun readUShort(x: UByte, y: UByte): UShort {
        return ((x.toInt() shl 8).toUShort() or y.toUShort())
    }

    private fun writeUShort(value: UShort, x: (UByte) -> Unit, y: (UByte) -> Unit) {
        x((value.toInt() and 0xFF00 shr 8).toUByte())
        y((value.toInt() and 0x00FF).toUByte())
    }

    var af: UShort
        get() = readUShort(a, f.toUByte())
        set(value) = writeUShort(value, { x -> a = x }, { x -> f = Flags(x) })
    var bc: UShort
        get() = readUShort(b, c)
        set(value) = writeUShort(value, { x -> b = x }, { x -> c = x })
    var de: UShort
        get() = readUShort(d, e)
        set(value) = writeUShort(value, { x -> d = x }, { x -> e = x })
    var hl: UShort
        get() = readUShort(h, l)
        set(value) = writeUShort(value, { x -> h = x }, { x -> l = x })
}

package gameboy.memory

data class MemoryBus(
    val memory: MutableList<UByte> = MutableList(size = 0xFFFF, init = { 0u })
) {
    operator fun get(index: UShort): UByte {
        return memory[index.toInt()]
    }

    operator fun set(index: UShort, data: UByte) {
        memory[index.toInt()] = data
    }
}

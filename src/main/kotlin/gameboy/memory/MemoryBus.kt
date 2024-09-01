package gameboy.memory

data class MemoryBus(
    val memory: List<UByte> = List(size = 0xFFFF, init = { 0u })
) {
    fun readByte(address: UShort) = memory[address.toInt()]
}

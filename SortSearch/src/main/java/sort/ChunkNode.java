package sort;

@SuppressWarnings ({"unchecked", "rawtypes"})
public class ChunkNode implements Comparable<ChunkNode> {
	Object value;
	int chunkIdx;
	int arrayIdx;
	
	public ChunkNode(Object value, int chunkIdx, int arrayIdx) {
		this.value = value;
		this.chunkIdx = chunkIdx;
		this.arrayIdx = arrayIdx;
	}
	
	@Override
	public int compareTo(ChunkNode other) {
		return ((Comparable)this.value).compareTo((Comparable)other.value);
	}
		
	@Override
	public boolean equals(Object other) {
		ChunkNode otherNode = (ChunkNode)other;
		return this.value.equals(otherNode.value);
	}
}

package board.vo;

import java.util.Date;

public class BoardVO {
	private String kind;
	private int idx;
	private String title;
	private String content;
	private int readcount;
	private int groupId;
	private int depth;
	private int groupSeq;
	private String writeId;
	private String writeName;
	private Date writeDay;
	private String filename;
	private int isdel;
	
	public BoardVO() {}
	
	//insert, update에 전달받아 사용하는 값이 무엇인가?
	//insert경우 입력하는 내용(title, content, filename)
	//update인 경우는 위와 동일
	public BoardVO(String title, String content, String filename) {
		this.title = title;
		this.content = content;
		this.filename = filename;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReadcount() {
		return readcount;
	}

	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getGroupSeq() {
		return groupSeq;
	}

	public void setGroupSeq(int groupSeq) {
		this.groupSeq = groupSeq;
	}

	public String getWriteId() {
		return writeId;
	}

	public void setWriteId(String writeId) {
		this.writeId = writeId;
	}

	public String getWriteName() {
		return writeName;
	}

	public void setWriteName(String writeName) {
		this.writeName = writeName;
	}

	public Date getWriteDay() {
		return writeDay;
	}

	public void setWriteDay(Date writeDay) {
		this.writeDay = writeDay;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	@Override
	public String toString() {
		return "BoardVO [kind=" + kind + ", idx=" + idx + ", title=" + title + ", content=" + content + ", readcount="
				+ readcount + ", groupId=" + groupId + ", depth=" + depth + ", groupSeq=" + groupSeq + ", writeId="
				+ writeId + ", writeName=" + writeName + ", writeDay=" + writeDay + ", filename=" + filename
				+ ", isdel=" + isdel + "]";
	}
	
}

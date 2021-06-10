package br.ufscar.dc.dsw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "FileDB")
public class FileEntity extends AbstractEntity<Long> {

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 30)
	private String type;

	@Lob
	private byte[] data;

	public FileEntity() {
	}

	public FileEntity(String name, String type, byte[] data) {
		this.name = name;
		this.type = type;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	public boolean isImage() {
		return this.type.contains("image");
	}
}
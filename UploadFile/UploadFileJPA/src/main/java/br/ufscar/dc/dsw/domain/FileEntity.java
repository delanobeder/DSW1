package br.ufscar.dc.dsw.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "FileDB")
public class FileEntity extends AbstractEntity<Long> {

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 30)
	private String type;

	@Lob
	@Basic
	@Column(length=10485760) // 10MB
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
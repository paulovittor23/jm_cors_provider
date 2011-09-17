package br.com.devmedia.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MessageBean {
	
	private String mensagem;
	
	public MessageBean() {}
	
	public MessageBean(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}


/**
 *
 */
package com.harmobeer.vo;

/**
 Classe que modela o objeto Harmonizacao
 *
 * @author Jose Carlos Soares da Cruz Junior / Luan Henrique Cunha Alves
 *
 */
public class Harmonizacao {


	private int id_harmo;
	private int id_cerv;
	private String nm_cerv;
	private int id_prato;
	private String nm_prato;
	private double media;
	/**
	 * Construtor completo do objeto Harmonizacao.
	 * @param id_harmo
	 * @param id_cerv
	 * @param id_prato
	 * @param media
	 *
	 *
	 */

	public Harmonizacao (int id_harmo, int id_cerv, int id_prato, double media) {
		this.id_harmo = id_harmo;
		this.id_cerv = id_cerv;
		this.id_prato = id_prato;
		this.media = media;
	}
	/**
	 * Construtor sem o id_harmo do objeto Harmonizacao. Utilizado para a inclus�o do objeto no banco de dados.
	 * @param id_cerv
	 * @param id_prato
	 * @param media
	 *
	 */

	public Harmonizacao (int id_cerv, int id_prato, double media) {
		this.id_cerv = id_cerv;
		this.id_prato = id_prato;
		this.media = media;
	}
	/**
	 * @param id_harmo
	 */
	public Harmonizacao(int id_harmo) {
		this.id_harmo = id_harmo;
	}

	/**
     * @param pId_harmo
     * @param pId_cerv
     * @param pNm_cerv
     * @param pId_prato
     * @param pNm_prato
     * @param pMedia
     */
    public Harmonizacao(int pId_harmo, int pId_cerv, String pNm_cerv, int pId_prato, String pNm_prato, double pMedia)
    {
        id_harmo = pId_harmo;
        id_cerv = pId_cerv;
        nm_cerv = pNm_cerv;
        id_prato = pId_prato;
        nm_prato = pNm_prato;
        media = pMedia;
    }
    /**Metodo construtor vazio para gerar objeto nulo
	 * @param
	 */
	public Harmonizacao() {

	}

	/**
	 * @return the id_harmo
	 */
	public int getId_harmo() {
		return id_harmo;
	}
	/**
	 * @param id_harmo the id_harmo to set
	 */
	public void setId_harmo(int id_harmo) {
		this.id_harmo = id_harmo;
	}
	/**
	 * @return the id_cerv
	 */
	public int getId_cerv() {
		return id_cerv;
	}
	/**
	 * @param id_cerv the id_cerv to set
	 */
	public void setId_cerv(int id_cerv) {
		this.id_cerv = id_cerv;
	}
	/**
	 * @return the id_prato
	 */
	public int getId_prato() {
		return id_prato;
	}
	/**
	 * @param id_prato the id_prato to set
	 */
	public void setId_prato(int id_prato) {
		this.id_prato = id_prato;
	}
	/**
	 * @return the media
	 */
	public double getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(double media) {
		this.media = media;
	}
    /**
     * @return the nm_cerv
     */
    public String getNm_cerv()
    {
        return nm_cerv;
    }
    /**
     * @param pNm_cerv the nm_cerv to set
     */
    public void setNm_cerv(String pNm_cerv)
    {
        nm_cerv = pNm_cerv;
    }
    /**
     * @return the nm_prato
     */
    public String getNm_prato()
    {
        return nm_prato;
    }
    /**
     * @param pNm_prato the nm_prato to set
     */
    public void setNm_prato(String pNm_prato)
    {
        nm_prato = pNm_prato;
    }


}

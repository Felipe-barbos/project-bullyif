package com.felipebc.bullyif.model;

import com.felipebc.bullyif.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Denuncia {
    private String identificacaoUsuario;
    private String idDenuncia;
    private String identificacaoVitima;
    private String identificacaoAgressor;
    private String localOcorrido;
    private String dataOcorrido;
    private String statusDenuncia;
    private String horarioOcorrido;
    private String tipoAgressao;
    private String descricao;
    private Usuario usuarioExibicao;

    public Denuncia() {
    }

    public void Salvar(){
        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebaseDatabase();
        DatabaseReference denuncia = firebaseRef.child("denuncias")
                .child(getIdDenuncia());

        denuncia.setValue(this);
    }

    public String getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(String idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    @Exclude
    public String getIdentificacaoUsuario() {
        return identificacaoUsuario;
    }

    public void setIdentificacaoUsuario(String identificacaoUsuario) {
        this.identificacaoUsuario = identificacaoUsuario;
    }

    public String getIdentificacaoVitima() {
        return identificacaoVitima;
    }

    public void setIdentificacaoVitima(String identificacaoVitima) {
        this.identificacaoVitima = identificacaoVitima;
    }

    public String getIdentificacaoAgressor() {
        return identificacaoAgressor;
    }

    public void setIdentificacaoAgressor(String identificacaoAgressor) {
        this.identificacaoAgressor = identificacaoAgressor;
    }

    public String getLocalOcorrido() {
        return localOcorrido;
    }

    public void setLocalOcorrido(String localOcorrido) {
        this.localOcorrido = localOcorrido;
    }

    public String getDataOcorrido() {
        return dataOcorrido;
    }

    public void setDataOcorrido(String dataOcorrido) {
        this.dataOcorrido = dataOcorrido;
    }

    public String getStatusDenuncia() {
        return statusDenuncia;
    }

    public void setStatusDenuncia(String statusDenuncia) {
        this.statusDenuncia = statusDenuncia;
    }

    public String getHorarioOcorrido() {
        return horarioOcorrido;
    }

    public void setHorarioOcorrido(String horarioOcorrido) {
        this.horarioOcorrido = horarioOcorrido;
    }

    public String getTipoAgressao() {
        return tipoAgressao;
    }

    public void setTipoAgressao(String tipoAgressao) {
        this.tipoAgressao = tipoAgressao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuarioExibicao() {
        return usuarioExibicao;
    }

    public void setUsuarioExibicao(Usuario usuarioExibicao) {
        this.usuarioExibicao = usuarioExibicao;
    }
}


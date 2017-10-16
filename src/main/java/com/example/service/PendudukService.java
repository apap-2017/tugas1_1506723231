package com.example.service;

import java.sql.Date;
import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface PendudukService
{
    KelurahanModel selectKelurahan (int id);
    List<KelurahanModel> selectAllKelurahan();
    List<KelurahanModel> selectAllKelurahanByIdKecamatan(int id_kecamatan);
    
    
    KecamatanModel selectKecamatan (int id);
    List<KecamatanModel> selectAllKecamatan();
    List<KecamatanModel> selectAllKecamatanByIdKota(int id_kota);
    
    KotaModel selectKota (int id);
    List<KotaModel> selectAllKota();
    
    PendudukModel selectPenduduk (String nik);
    Date selectTermudaDummy(int id_kelurahan);
    Date selectTertuaDummy(int id_kelurahan);
    PendudukModel selectPendudukTermuda(int id_kelurahan);
    PendudukModel selectPendudukTertua(int id_kelurahan);
    List<PendudukModel> selectPendudukByIdKeluarga (int id_keluarga);
    List<PendudukModel> selectPendudukByIdKelurahan (int id_kelurahan);
    
    KeluargaModel selectKeluarga (int id);
    
    List<String> selectNIKMirip(String nik);
    
    List<PendudukModel> selectPendudukMirip(String nik);
    
    int selectIDMax();

    void addPenduduk (PendudukModel penduduk);
    
    PendudukModel updateAndGetPenduduk (PendudukModel penduduk);

    void setIsWafat (PendudukModel penduduk);
    
    void setIsTidakBerlaku (KeluargaModel keluarga);
}

package com.example.service;

import java.util.List;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

public interface KeluargaService
{
    KeluargaModel selectKeluarga (String nkk);
    KelurahanModel selectKelurahan (int id);
    KecamatanModel selectKecamatan (int id);
    KotaModel selectKota (int id);
    List<PendudukModel> selectPendudukKeluarga (int nkk); 
    
    List<KelurahanModel> selectAllKelurahan();
    
    List<String> selectNKKMirip(String nkk);
    
    List<KeluargaModel> selectKeluargaMirip(String nkk);
    
    int selectIDMax();
    
    void addKeluarga (KeluargaModel keluarga);
    
    KeluargaModel updateAndGetKeluarga (KeluargaModel keluarga);

//    void addKeluarga (KeluargaModel keluarga);
//
//    void deleteKeluarga (String nkk);
//    
//    void updateKeluarga (KeluargaModel keluarga);
}

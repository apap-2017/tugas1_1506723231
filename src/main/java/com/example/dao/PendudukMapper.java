package com.example.dao;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

@Mapper
public interface PendudukMapper
{   
    @Select("SELECT * FROM kelurahan WHERE id = #{id}")
    KelurahanModel selectKelurahan (@Param("id") int id);
    
    @Select("SELECT * FROM kelurahan")
    List<KelurahanModel> selectAllKelurahan();
    
    @Select("SELECT * FROM kelurahan where id_kecamatan = #{id_kecamatan}")
    List<KelurahanModel> selectAllKelurahanByIdKecamatan(@Param("id_kecamatan") int id_kecamatan);
    
    @Select("SELECT * FROM kecamatan WHERE id = #{id}")
    KecamatanModel selectKecamatan (@Param("id") int id);
    
    @Select("SELECT * FROM kecamatan")
    List<KecamatanModel> selectAllKecamatan();
    
    @Select("SELECT * FROM kecamatan where id_kota = #{id_kota}")
    List<KecamatanModel> selectAllKecamatanByIdKota(@Param("id_kota") int id_kota);
    
    @Select("SELECT * FROM kota WHERE id = #{id}")
    KotaModel selectKota (@Param("id") int id);
    
    @Select("SELECT * FROM kota")
    List<KotaModel> selectAllKota();

    @Select("SELECT * FROM penduduk WHERE nik = #{nik}")
    PendudukModel selectPenduduk (@Param("nik") String nik);
    
    @Select("SELECT * FROM keluarga WHERE id = #{id}")
    KeluargaModel selectKeluarga (@Param("id") int id);
    
    @Select("SELECT nik FROM penduduk WHERE nik LIKE '%" + "${nik}" + "%'")
    List<String> selectNIKMirip(@Param("nik") String nik);

    @Select("SELECT * FROM penduduk WHERE nik LIKE '%" + "${nik}" + "%'")
    List<PendudukModel> selectPendudukMirip(@Param("nik") String nik);
    
    @Select("SELECT max(id) FROM penduduk")
    int selectIDMax();

    @Insert("INSERT INTO penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void addPenduduk (PendudukModel penduduk);
    
    @Update("UPDATE penduduk SET nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} WHERE nik = #{nik_previous}")
    void updatePenduduk (PendudukModel penduduk);
    
    @Update("UPDATE penduduk SET is_wafat = #{is_wafat} WHERE nik = #{nik}")
    void setIsWafat (PendudukModel penduduk);
    
    @Select("SELECT * FROM penduduk LEFT JOIN keluarga ON penduduk.id_keluarga = keluarga.id WHERE keluarga.id = #{id_keluarga}")
    List<PendudukModel> selectPendudukByIdKeluarga (@Param("id_keluarga") int id_keluarga);
    
    @Select("SELECT * FROM penduduk LEFT JOIN keluarga ON penduduk.id_keluarga = keluarga.id WHERE keluarga.id_kelurahan = #{id_kelurahan}")
    List<PendudukModel> selectPendudukByIdKelurahan (@Param("id_kelurahan") int id_kelurahan);
    
    @Update("UPDATE keluarga SET is_tidak_berlaku = #{is_tidak_berlaku} WHERE id = #{id}")
    void setIsTidakBerlaku (KeluargaModel keluarga);
    
    @Select("SELECT MAX(tanggal_lahir) FROM penduduk LEFT JOIN keluarga ON penduduk.id_keluarga = keluarga.id WHERE keluarga.id_kelurahan = #{id_kelurahan} LIMIT 1")
    Date selectTermudaDummy (@Param("id_kelurahan") int id_kelurahan);
    
    @Select("SELECT MIN(tanggal_lahir) FROM penduduk LEFT JOIN keluarga ON penduduk.id_keluarga = keluarga.id WHERE keluarga.id_kelurahan = #{id_kelurahan} LIMIT 1")
    Date selectTertuaDummy (@Param("id_kelurahan") int id_kelurahan);
    
    @Select("SELECT * FROM penduduk LEFT JOIN keluarga ON penduduk.id_keluarga = keluarga.id WHERE keluarga.id_kelurahan = #{id_kelurahan} AND penduduk.tanggal_lahir = #{tanggal_lahir} LIMIT 1")
    PendudukModel selectPendudukTermuda (@Param("id_kelurahan") int id_kelurahan, @Param("tanggal_lahir") Date tanggal_lahir);
    
    @Select("SELECT * FROM penduduk LEFT JOIN keluarga ON penduduk.id_keluarga = keluarga.id WHERE keluarga.id_kelurahan = #{id_kelurahan} AND penduduk.tanggal_lahir = #{tanggal_lahir} LIMIT 1")
    PendudukModel selectPendudukTertua (@Param("id_kelurahan") int id_kelurahan, @Param("tanggal_lahir") Date tanggal_lahir);
}

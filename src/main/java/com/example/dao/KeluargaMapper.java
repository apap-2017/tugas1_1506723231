package com.example.dao;

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
public interface KeluargaMapper
{
    @Select("SELECT * FROM keluarga WHERE nomor_kk = #{nkk}")
    KeluargaModel selectKeluarga (@Param("nkk") String nkk);
    
    @Select("SELECT * FROM kelurahan WHERE id = #{id}")
    KelurahanModel selectKelurahan (@Param("id") int id);
    
    @Select("SELECT * FROM kecamatan WHERE id = #{id}")
    KecamatanModel selectKecamatan (@Param("id") int id);
    
    @Select("SELECT * FROM kota WHERE id = #{id}")
    KotaModel selectKota (@Param("id") int id);
    
    @Insert("INSERT INTO keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) VALUES (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga (KeluargaModel keluarga);
    
    @Select("SELECT nomor_kk FROM keluarga WHERE nomor_kk LIKE '%" + "${nkk}" + "%'")
    List<String> selectNKKMirip(@Param("nkk") String nkk);

    @Select("SELECT * FROM keluarga WHERE nomor_kk LIKE '%" + "${nkk}" + "%'")
    List<KeluargaModel> selectKeluargaMirip(@Param("nkk") String nkk);
    
    @Select("SELECT max(id) FROM keluarga")
    int selectIDMax();
    
    @Select("SELECT * FROM kelurahan")
    List<KelurahanModel> selectAllKelurahan();

    @Select("SELECT * FROM penduduk WHERE id_keluarga = #{idKeluarga}")
    List<PendudukModel> selectPendudukKeluarga (@Param("idKeluarga") int idKeluarga);
    
    @Update("UPDATE keluarga SET nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan} WHERE nomor_kk = #{nomor_kk_previous}")
    void updateKeluarga (KeluargaModel keluarga);

//    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
//    void addKeluarga (KeluargaModel keluarga);
//    
//    @Delete("DELETE FROM student WHERE npm = #{npm}")
//    void deleteKeluarga (@Param("npm") String nkk);
//    
//    @Update("UPDATE student SET npm = #{npm}, name = #{name}, gpa = #{gpa} WHERE npm = #{npm}")
//    void updateKeluarga (KeluargaModel keluarga);
}

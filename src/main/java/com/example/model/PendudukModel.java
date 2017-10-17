package com.example.model;

import java.sql.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel
{
	private int id;
    
    private String nik;
    
//  nik untuk menyimpan nik sebelumnya jika ada perubahan
    private String nik_previous;
    
    @NotEmpty
    private String nama;
    
    private String tempat_lahir;
    
    private int id_kecamatan;
    
    @NotNull
    private Date tanggal_lahir;
    
    @NotNull
    private int jenis_kelamin;
    
    private int is_wni;
    
    @NotEmpty
    private String kewarganegaraan;
    
    @NotNull
    private int id_keluarga;
    
    @NotEmpty
    private String agama;
    
    @NotEmpty
    private String pekerjaan;
    
    @NotEmpty
    private String status_perkawinan;
    
    @NotEmpty
    private String status_dalam_keluarga;
    
    @NotEmpty
    private String golongan_darah;
    
    private int is_wafat;

}

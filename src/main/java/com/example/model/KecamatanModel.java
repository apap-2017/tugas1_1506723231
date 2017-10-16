package com.example.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel
{
    @NotNull
	private int id;
    
    @NotNull
    private int id_kota;
    
    @NotNull
    private String kode_kecamatan;
    
    @NotNull
    private String nama_kecamatan;

}

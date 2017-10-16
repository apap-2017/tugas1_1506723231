package com.example.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel
{
    @NotNull
	private int id;
    
    @NotNull
    private int id_kecamatan;
    
    @NotNull
    private String kode_kelurahan;
    
    @NotNull
    private String nama_kelurahan;
    
    @NotNull
    private String kode_pos;
}

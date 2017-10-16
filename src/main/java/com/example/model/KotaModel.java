package com.example.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KotaModel
{
    @NotNull
	private int id;
    
    @NotNull
    private String kode_kota;
    
    @NotNull
    private String nama_kota;

}

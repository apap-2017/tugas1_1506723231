package com.example.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel
{
	private int id;
    
    private String nomor_kk;
    
    private String nomor_kk_previous;
    
    @NotEmpty
    private String alamat;
    
    @NotEmpty
    private String rt;
    
    @NotEmpty
    private String rw;
    
    @NotNull
    private int id_kelurahan;
    
    private int is_tidak_berlaku;
}

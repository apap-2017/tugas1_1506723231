package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;
import com.example.service.PendudukService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PendudukController {
	@Autowired
	PendudukService pendudukDAO;
	
	@RequestMapping("/penduduk")
	public String indexPenduduk(@RequestParam(value = "nik", required = true) String nik, Model model) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		KeluargaModel keluarga = pendudukDAO.selectKeluarga(penduduk.getId_keluarga());
		KelurahanModel kelurahan = pendudukDAO.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = pendudukDAO.selectKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = pendudukDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("kota", kota);
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("title", "Search Penduduk");
		
		return "penduduk/hasilSearchPenduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah")
    public String tambahPenduduk(Model model) {
		PendudukModel penduduk = new PendudukModel();
		List<KecamatanModel> kecamatans = pendudukDAO.selectAllKecamatan();
		
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("kecamatans", kecamatans);
		model.addAttribute("title", "Tambah Penduduk");
		
    		return "penduduk/tambahPenduduk";
    }
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
    public String tambahPendudukSubmit(Model model, @Valid @ModelAttribute("penduduk") PendudukModel penduduk, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<KecamatanModel> kecamatans = pendudukDAO.selectAllKecamatan();
			
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("kecamatans", kecamatans);
			model.addAttribute("title", "Tambah Penduduk");
			
	    		return "penduduk/tambahPenduduk";
		}
		
    		pendudukDAO.addPenduduk(penduduk);
    		model.addAttribute("penduduk", penduduk);
    		model.addAttribute("title", "Tambah Penduduk");
		
    		return "penduduk/tambahPendudukSubmit";
    }
	
	@RequestMapping("/penduduk/ubah")
    public String ubahPendudukKosong (Model model) {
		model.addAttribute("title", "Ubah Data Penduduk");
		
		return "penduduk/ubahPendudukKosong";
	}
	
	@RequestMapping(value = "/penduduk/ubah", method = RequestMethod.POST)
    public String ubahPendudukKosongSubmit (Model model, @RequestParam(value="nik", required=true) String nik) {
		return "redirect:/penduduk/ubah/" + nik;
	}
	
	@RequestMapping("/penduduk/ubah/{nik}")
    public String ubahPenduduk (Model model, @PathVariable(value = "nik") String nik) {
		PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);
		List<KecamatanModel> kecamatans = pendudukDAO.selectAllKecamatan();
		
		model.addAttribute("title", "Ubah Data Penduduk");
		model.addAttribute("penduduk", penduduk);
		model.addAttribute("kecamatans", kecamatans);
		
		return "penduduk/ubahPenduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{nik}", method = RequestMethod.POST)
    public String ubahPendudukSubmit(Model model, @Valid @ModelAttribute("penduduk") PendudukModel penduduk, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<KecamatanModel> kecamatans = pendudukDAO.selectAllKecamatan();
			
			model.addAttribute("penduduk", penduduk);
			model.addAttribute("kecamatans", kecamatans);
			model.addAttribute("title", "Ubah Data Penduduk");
			
			return "penduduk/ubahPenduduk";
		}
		
    		penduduk = pendudukDAO.updateAndGetPenduduk(penduduk);
    		model.addAttribute("penduduk", penduduk);
    		model.addAttribute("title", "Ubah Data Penduduk");
		
    		return "penduduk/ubahPendudukSubmit";
    }
	
	@RequestMapping(value="/penduduk/mati", method = RequestMethod.POST)
	public String ubahKematianPendudukSubmit(Model model, @ModelAttribute("penduduk") PendudukModel penduduk) {
		log.info("nik penduduk " + penduduk.getNik());
		
		model.addAttribute("penduduk", penduduk);
		
		pendudukDAO.setIsWafat(penduduk);
		
		return "redirect:/penduduk/?nik=" + penduduk.getNik();
	}
	
	@RequestMapping("/penduduk/cari")
    public String cariPenduduk (
            @RequestParam(value = "kt", required = false) String kt,
            @RequestParam(value = "kc", required = false) String kc,
            @RequestParam(value = "kl", required = false) String kl,
            Model model)
    {
		if (kl != null) {
			KotaModel kota = pendudukDAO.selectKota(Integer.parseInt(kt));
			KecamatanModel kecamatan = pendudukDAO.selectKecamatan(Integer.parseInt(kc));
			KelurahanModel kelurahan = pendudukDAO.selectKelurahan(Integer.parseInt(kl));
			
			List<PendudukModel> penduduk = pendudukDAO.selectPendudukByIdKelurahan(Integer.parseInt(kl));
			PendudukModel pendudukTermuda = pendudukDAO.selectPendudukTermuda(Integer.parseInt(kl));
			PendudukModel pendudukTertua = pendudukDAO.selectPendudukTertua(Integer.parseInt(kl));
			
			model.addAttribute("penduduks", penduduk);
			model.addAttribute("pendudukTermuda", pendudukTermuda);
			model.addAttribute("pendudukTertua", pendudukTertua);
			model.addAttribute("title", "Cari Penduduk");
			model.addAttribute("kota", kota);
			model.addAttribute("kecamatan", kecamatan);
			model.addAttribute("kelurahan", kelurahan);
			
	    		return "penduduk/cariPendudukKelurahan";
	    }
        else if (kc != null) {
	        	KecamatanModel kecamatan = pendudukDAO.selectKecamatan(Integer.parseInt(kc));
	    		KotaModel kota = pendudukDAO.selectKota(Integer.parseInt(kt));
        		List<KelurahanModel> kelurahan = pendudukDAO.selectAllKelurahanByIdKecamatan(kecamatan.getId());
	        	
	    		model.addAttribute("kota", kota);
	    		model.addAttribute("kecamatan", kecamatan);
	    		model.addAttribute("kelurahans", kelurahan);
        	
        		return "penduduk/cariPendudukKecamatan";
        }
        else if (kt != null) {
        		KotaModel kota = pendudukDAO.selectKota(Integer.parseInt(kt));
        		List<KecamatanModel> kecamatan = pendudukDAO.selectAllKecamatanByIdKota(kota.getId());
        	
        		model.addAttribute("kota", kota);
        		model.addAttribute("kecamatans", kecamatan);
        	
	    		return "penduduk/cariPendudukKota";
	    }
        else {
        		List<KotaModel> kota = pendudukDAO.selectAllKota();
        		model.addAttribute("kotas", kota);
        		model.addAttribute("title", "Cari Penduduk");
        		
        		return "penduduk/cariPenduduk";
        }
    }
}

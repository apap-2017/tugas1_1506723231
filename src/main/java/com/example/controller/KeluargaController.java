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
import com.example.service.KeluargaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KeluargaController {
	@Autowired
	KeluargaService keluargaDAO;
	
	@RequestMapping("/keluarga")
	public String index(@RequestParam(value = "nkk", required = true) String nkk, Model model) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		List<PendudukModel> penduduk = keluargaDAO.selectPendudukKeluarga(keluarga.getId());
		KelurahanModel kelurahan = keluargaDAO.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = keluargaDAO.selectKecamatan(kelurahan.getId_kecamatan());
		KotaModel kota = keluargaDAO.selectKota(kecamatan.getId_kota());
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("penduduks", penduduk);
		model.addAttribute("kecamatan", kecamatan);
		model.addAttribute("kelurahan", kelurahan);
		model.addAttribute("kota", kota);
		model.addAttribute("title", "Search Keluarga");
		
		return "keluarga/hasilSearchKeluarga";
	}
	
	@RequestMapping("/keluarga/tambah")
	public String tambahKeluarga(Model model) {
		KeluargaModel keluarga = new KeluargaModel();
		List<KelurahanModel> kelurahans = keluargaDAO.selectAllKelurahan();
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("kelurahans", kelurahans);
		model.addAttribute("title", "Tambah Keluarga");
		
		return "keluarga/tambahKeluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
    public String tambahKeluargaSubmit(Model model, @Valid @ModelAttribute("keluarga") KeluargaModel keluarga, BindingResult bindingResult) {
		model.addAttribute("title", "Tambah Keluarga");
		
		if (bindingResult.hasErrors()) {
			List<KelurahanModel> kelurahans = keluargaDAO.selectAllKelurahan();
			
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahans", kelurahans);
			
			return "keluarga/tambahKeluarga";
		}
		
    		keluargaDAO.addKeluarga(keluarga);
    		model.addAttribute("keluarga", keluarga);
    		
		
    		return "keluarga/tambahKeluargaSubmit";
    }
	
	@RequestMapping("/keluarga/ubah")
    public String ubahKeluargaKosong (Model model) {
		model.addAttribute("title", "Ubah Data Keluarga");
		
		return "keluarga/ubahKeluargaKosong";
	}
	
	@RequestMapping(value = "/keluarga/ubah", method = RequestMethod.POST)
    public String ubahKeluargaKosongSubmit (Model model, @RequestParam(value="nkk", required=true) String nkk) {
		return "redirect:/keluarga/ubah/" + nkk;
	}
	
	@RequestMapping("/keluarga/ubah/{nkk}")
	public String tambahKeluarga(Model model, @PathVariable(value = "nkk") String nkk) {
		KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);
		List<KelurahanModel> kelurahans = keluargaDAO.selectAllKelurahan();
		
		model.addAttribute("keluarga", keluarga);
		model.addAttribute("kelurahans", kelurahans);
		model.addAttribute("title", "Ubah Data Keluarga");
		
		return "keluarga/ubahKeluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah/{nkk}", method = RequestMethod.POST)
    public String ubahKeluargaSubmit(Model model, @Valid @ModelAttribute("keluarga") KeluargaModel keluarga, BindingResult bindingResult) {
		model.addAttribute("title", "Ubah Data Keluarga");
		
		if (bindingResult.hasErrors()) {
			List<KelurahanModel> kelurahans = keluargaDAO.selectAllKelurahan();
			
			model.addAttribute("keluarga", keluarga);
			model.addAttribute("kelurahans", kelurahans);
			
			return "keluarga/ubahKeluarga";
		}
		
    		keluarga = keluargaDAO.updateAndGetKeluarga(keluarga);
    		model.addAttribute("keluarga", keluarga);
		
    		return "keluarga/ubahKeluargaSubmit";
    }
	
	
}

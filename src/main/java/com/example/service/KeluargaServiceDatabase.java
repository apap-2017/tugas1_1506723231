package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.KeluargaMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override
	public KeluargaModel selectKeluarga(String nkk) {
		log.info("select keluarga with nkk {}", nkk);
		return keluargaMapper.selectKeluarga(nkk);
	}
	
	@Override
	public KelurahanModel selectKelurahan(int id) {
		log.info("select kelurahan with id {}", id);
		return keluargaMapper.selectKelurahan(id);
	}

	@Override
	public KecamatanModel selectKecamatan(int id) {
		log.info("select kecamatan with id {}", id);
		return keluargaMapper.selectKecamatan(id);
	}

	@Override
	public KotaModel selectKota(int id) {
		log.info("select kota with id {}", id);
		return keluargaMapper.selectKota(id);
	}

	@Override
	public List<PendudukModel> selectPendudukKeluarga(int idKeluarga) {
		log.info("select penduduk with idKeluarga {}", idKeluarga);
		return keluargaMapper.selectPendudukKeluarga(idKeluarga);
	}

	@Override
	public List<KelurahanModel> selectAllKelurahan() {
		log.info("select all kecamatan");
		return keluargaMapper.selectAllKelurahan();
	}

	@Override
	public int selectIDMax() {
		log.info("select id max");
		return keluargaMapper.selectIDMax();
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		log.info("add keluarga with id {}", keluarga.getId());
		keluarga.setNomor_kk(this.generateNKK(keluarga));
		keluarga.setId(this.selectIDMax() + 1);
		
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public List<String> selectNKKMirip(String nkk) {
		log.info("select nkk mirip dengan nkk {}", nkk);
		return keluargaMapper.selectNKKMirip(nkk);
	}

	@Override
	public List<KeluargaModel> selectKeluargaMirip(String nkk) {
		log.info("select keluarga mirip dengan nkk {}", nkk);
		return keluargaMapper.selectKeluargaMirip(nkk);
	}

	@Override
	public KeluargaModel updateAndGetKeluarga(KeluargaModel keluarga) {
		log.info("update keluarga dengan id {}", keluarga.getId());
		
		keluarga.setNomor_kk_previous(keluarga.getNomor_kk());
		keluarga.setNomor_kk(this.generateNKK(keluarga));
		keluargaMapper.updateKeluarga(keluarga);
		
		return keluarga;
	}
	
	private String generateNKK(KeluargaModel keluarga) {
		String nkk = "";
		
		KelurahanModel kelurahan = this.selectKelurahan(keluarga.getId_kelurahan());
		KecamatanModel kecamatan = this.selectKecamatan(kelurahan.getId_kecamatan());
		String kode_kecamatan = kecamatan.getKode_kecamatan().toString();
		
		nkk += "" + kode_kecamatan.substring(0, kode_kecamatan.length() - 1);
		
		String[] tanggalIsi = java.time.LocalDate.now().toString().split("-");
		
		nkk += tanggalIsi[2] + tanggalIsi[1] + tanggalIsi[0].substring(2);
		
		if (keluarga.getId() == 0) {
			return nkkDigitAkhir(keluarga, nkk);
		}
		else {
			List<KeluargaModel> keluargaNKKMirip = this.selectKeluargaMirip(nkk);
			
			for (int i = 0; i < keluargaNKKMirip.size(); i++) {
				if (keluargaNKKMirip.get(i).getId() == keluarga.getId()) {
					if (keluargaNKKMirip.get(i).getNomor_kk().contains(nkk)) {
						log.info("masuk ke id dan nik yang sama");
						return keluargaNKKMirip.get(i).getNomor_kk();
					}
					else {
						break;
					}
				}
			}
			
			return nkkDigitAkhir(keluarga, nkk);
		}
	}
	
	private String nkkDigitAkhir(KeluargaModel keluarga, String nkk) {
		List<String> nkkMirip = this.selectNKKMirip(nkk);
		int digitTerakhir = 1;
		while (true) {
			String nkkSementara = nkk;
			String digitTerakhirNKK = "" + digitTerakhir; 
			while (digitTerakhirNKK.length() < 4) {
				digitTerakhirNKK = "0" + digitTerakhirNKK;
			}
			nkkSementara += digitTerakhirNKK;
			
			if (!nkkMirip.contains(nkkSementara)) {
				log.info("nkk " + nkk);
				return nkkSementara;
			}
			
			digitTerakhir++;
		}
	}
}

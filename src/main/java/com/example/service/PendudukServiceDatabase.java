package com.example.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PendudukMapper;
import com.example.model.KecamatanModel;
import com.example.model.KeluargaModel;
import com.example.model.KelurahanModel;
import com.example.model.KotaModel;
import com.example.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override
	public KelurahanModel selectKelurahan(int id) {
		log.info("select kelurahan with id {}", id);
		return pendudukMapper.selectKelurahan(id);
	}

	@Override
	public KecamatanModel selectKecamatan(int id) {
		log.info("select kecamatan with id {}", id);
		return pendudukMapper.selectKecamatan(id);
	}

	@Override
	public KotaModel selectKota(int id) {
		log.info("select kota with id {}", id);
		return pendudukMapper.selectKota(id);
	}

	@Override
	public PendudukModel selectPenduduk(String nik) {
		log.info("select penduduk with nik {}", nik);
		
		PendudukModel penduduk = pendudukMapper.selectPenduduk(nik);
		
		if (penduduk.getIs_wni() == 1) {
			penduduk.setKewarganegaraan("Indonesia");
		}
		else {
			penduduk.setKewarganegaraan("Asing");
		}
		
		return penduduk;
	}
	
	@Override
	public KeluargaModel selectKeluarga(int id) {
		log.info("select keluarga with id {}", id);
		return pendudukMapper.selectKeluarga(id);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		if (penduduk.getKewarganegaraan().equalsIgnoreCase("indonesia")) {
			penduduk.setIs_wni(1);
		}
		else {
			penduduk.setIs_wni(0);
		}
	
		penduduk.setTempat_lahir(this.selectKecamatan(penduduk.getId_kecamatan()).getNama_kecamatan());
		
		penduduk.setNik(this.generateNIK(penduduk));
		penduduk.setId(this.selectIDMax() + 1);
		
		log.info("nik penduduk " + penduduk.getNik());
		
		pendudukMapper.addPenduduk(penduduk);
	}
	
	private String generateNIK(PendudukModel penduduk) {
		String nik = "";
		KecamatanModel kecamatan = this.selectKecamatan(penduduk.getId_kecamatan());
		String kode_kecamatan = kecamatan.getKode_kecamatan().toString();
		
		nik += "" + kode_kecamatan.substring(0, kode_kecamatan.length() - 1);
		
		String[] tanggalLahir = penduduk.getTanggal_lahir().toString().split("-");
		
		if (penduduk.getJenis_kelamin() == 1) {
			tanggalLahir[2] = "" + (Integer.parseInt(tanggalLahir[2]) + 50);
		}
		
		nik += tanggalLahir[2] + tanggalLahir [1] + tanggalLahir[0].substring(2);
		
		if (penduduk.getId() == 0) {
			return nikDigitAkhir(penduduk, nik);
		}
		else {
			List<PendudukModel> pendudukNIKMirip = this.selectPendudukMirip(nik);
			
			for (int i = 0; i < pendudukNIKMirip.size(); i++) {
				if (pendudukNIKMirip.get(i).getId() == penduduk.getId()) {
					if (pendudukNIKMirip.get(i).getNik().contains(nik)) {
						log.info("masuk ke id dan nik yang sama");
						return pendudukNIKMirip.get(i).getNik();
					}
					else {
						break;
					}
				}
			}
			
			return nikDigitAkhir(penduduk, nik);
		}
	}
	
	private String nikDigitAkhir(PendudukModel penduduk, String nik) {
		List<String> nikMirip = this.selectNIKMirip(nik);
		int digitTerakhir = 1;
		while (true) {
			String nikSementara = nik;
			String digitTerakhirNIK = "" + digitTerakhir; 
			while (digitTerakhirNIK.length() < 4) {
				digitTerakhirNIK = "0" + digitTerakhirNIK;
			}
			nikSementara += digitTerakhirNIK;
			
			if (!nikMirip.contains(nikSementara)) {
				log.info("nik " + nik);
				return nikSementara;
			}
			
			digitTerakhir++;
		}
	}
	
	@Override
	public int selectIDMax() {
		int idMax = pendudukMapper.selectIDMax();
		log.info("id max ", idMax);
		return idMax;
	}
	
	@Override
	public List<KecamatanModel> selectAllKecamatan() {
		log.info("select all kecamatan");
		return pendudukMapper.selectAllKecamatan();
	}
	
	@Override
	public List<String> selectNIKMirip(String nik) {
		log.info("search penduduk with nik {}", nik);
		return pendudukMapper.selectNIKMirip(nik);
	}
	
	@Override
	public List<PendudukModel> selectPendudukMirip(String nik) {
		log.info("search penduduk with nik {}", nik);
		return pendudukMapper.selectPendudukMirip(nik);
	}
	
	@Override
	public PendudukModel updateAndGetPenduduk(PendudukModel penduduk) {
		log.info("update penduduk with nik {}", penduduk.getNik());
		
		if (penduduk.getKewarganegaraan().equalsIgnoreCase("indonesia")) {
			penduduk.setIs_wni(1);
		}
		else {
			penduduk.setIs_wni(0);
		}
	
		penduduk.setTempat_lahir(this.selectKecamatan(penduduk.getId_kecamatan()).getNama_kecamatan());
		penduduk.setNik_previous(penduduk.getNik());
		
		penduduk.setNik(this.generateNIK(penduduk));
		
		pendudukMapper.updatePenduduk(penduduk);
		
		return penduduk;
	}

	@Override
	public void setIsWafat(PendudukModel penduduk) {
		log.info("set wafat penduduk with nik {}", penduduk.getNik());
		
		penduduk.setIs_wafat(1);
		pendudukMapper.setIsWafat(penduduk);
		
		List<PendudukModel> pendudukByIdKeluarga = this.selectPendudukByIdKeluarga(penduduk.getId_keluarga());
		
		boolean isWafatSemua = true;
		for (int i = 0; i < pendudukByIdKeluarga.size(); i++) {
			if (pendudukByIdKeluarga.get(i).getIs_wafat() == 0) {
				isWafatSemua = false;
				break;
			}
		}
		
		if (isWafatSemua) {
			KeluargaModel keluargaWafatAnggota = this.selectKeluarga(penduduk.getId_keluarga());
			keluargaWafatAnggota.setIs_tidak_berlaku(1);
			
			this.setIsTidakBerlaku(keluargaWafatAnggota);
		}
	}

	@Override
	public List<PendudukModel> selectPendudukByIdKeluarga(int id_keluarga) {
		log.info("get penduduk with id_keluarga {}", id_keluarga);
		return pendudukMapper.selectPendudukByIdKeluarga(id_keluarga);
	}

	@Override
	public void setIsTidakBerlaku(KeluargaModel keluarga) {
		log.info("set is_tidak_berlaku with id_keluarga {}", keluarga.getId());
		pendudukMapper.setIsTidakBerlaku(keluarga);
	}

	@Override
	public List<KotaModel> selectAllKota() {
		log.info("select all kota");
		return pendudukMapper.selectAllKota();
	}

	@Override
	public List<KelurahanModel> selectAllKelurahan() {
		log.info("select all kelurahan");
		return pendudukMapper.selectAllKelurahan();
	}

	@Override
	public List<KelurahanModel> selectAllKelurahanByIdKecamatan(int id_kecamatan) {
		log.info("select all kelurahan by id kecamatan");
		return pendudukMapper.selectAllKelurahanByIdKecamatan(id_kecamatan);
	}

	@Override
	public List<KecamatanModel> selectAllKecamatanByIdKota(int id_kota) {
		log.info("select all kecamatan by id kota");
		return pendudukMapper.selectAllKecamatanByIdKota(id_kota);
	}

	@Override
	public List<PendudukModel> selectPendudukByIdKelurahan(int id_kelurahan) {
		log.info("select keluarga by id kelurahan {}", id_kelurahan);
		return pendudukMapper.selectPendudukByIdKelurahan(id_kelurahan);
	}

	@Override
	public PendudukModel selectPendudukTermuda(int id_kelurahan) {
		PendudukModel penduduk = pendudukMapper.selectPendudukTermuda(id_kelurahan, this.selectTermudaDummy(id_kelurahan));
		log.info("select penduduk termuda dengan nik: {}", penduduk.getNik());
		return penduduk;
	}

	@Override
	public PendudukModel selectPendudukTertua(int id_kelurahan) {
		PendudukModel penduduk = pendudukMapper.selectPendudukTertua(id_kelurahan, this.selectTertuaDummy(id_kelurahan));
		log.info("select penduduk tertua dengan nik: {}", penduduk.getNik());
		return penduduk;
	}

	@Override
	public Date selectTermudaDummy(int id_kelurahan) {
		Date tanggal_lahir = pendudukMapper.selectTermudaDummy(id_kelurahan); 
		log.info("select tanggal termuda = {}", tanggal_lahir);
		return tanggal_lahir;
	}

	@Override
	public Date selectTertuaDummy(int id_kelurahan) {
		Date tanggal_lahir = pendudukMapper.selectTertuaDummy(id_kelurahan);
		log.info("select tanggal tertua = {}", tanggal_lahir);
		return tanggal_lahir;
	}
}

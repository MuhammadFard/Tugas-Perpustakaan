/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.farid.pengembalian.service;

import com.farid.pengembalian.VO.ResponseTemplateVO;
import com.farid.pengembalian.VO.ResponseTemplateVOPinjam;
import com.farid.pengembalian.entity.Pengembalian;
import com.farid.pengembalian.repository.PengembalianRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;



/**
 *
 * @author IIISI
 */
@Service
public class PengembalianService {
    
    @Autowired
    private SimpleDateFormat simpleDateFormat;

    @Autowired
    private PengembalianRepository pengembalianRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Pengembalian savePengembalian(Pengembalian pengembalian) throws ParseException {
        ResponseTemplateVOPinjam peminjaman
                = restTemplate.getForObject("http://localhost:9002/peminjaman/"
                        + pengembalian.getPeminjamanId(), ResponseTemplateVOPinjam.class);
        String tglsekarang = simpleDateFormat.format(new Date());
        long terlambat = kurangTanggal(tglsekarang, peminjaman.getPeminjaman().getTglkembali());
        double denda = terlambat * 500;
        pengembalian.setTglDiKembalikan(tglsekarang); 
        pengembalian.setTerlambat((int) terlambat);
        pengembalian.setDenda(denda);
        return pengembalianRepository.save(pengembalian);
    }

    private long kurangTanggal(String tglAwal, String tglAkhir) throws ParseException {
        //SimpleDateFormat formatTanggal = new SimpleDateFormat("dd/MM/yyyy");
        Date tgl1 = simpleDateFormat.parse(tglAwal);
        Date tgl2 = simpleDateFormat.parse(tglAkhir);
        long selisih = tgl1.getTime() - tgl2.getTime();
        long selisihHari = selisih / (24 * 60 * 60 * 1000);
        return selisihHari;
    }

    public ResponseTemplateVO getPengembalian(Long pengembalianId) {
        ResponseTemplateVO vo = new ResponseTemplateVO();
        Pengembalian pengembalian
                = pengembalianRepository.findByPengembalianId(pengembalianId);
        ResponseTemplateVOPinjam peminjaman
                = restTemplate.getForObject("http://localhost:9002/peminjaman/"
                        + pengembalian.getPeminjamanId(), ResponseTemplateVOPinjam.class);

        vo.setPeminjaman(peminjaman.getPeminjaman());
        vo.setPengembalian(pengembalian);
        return vo;
    }
}

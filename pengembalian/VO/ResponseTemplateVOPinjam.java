/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.farid.pengembalian.VO;

import com.farid.pengembalian.VO.Buku;
import com.farid.pengembalian.VO.Anggota;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author IIISI
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateVOPinjam {
    private Peminjaman peminjaman;
    private Anggota anggota;
    private Buku buku;
}

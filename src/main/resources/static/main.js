//Fetch All Data Employee
$(document).ready(function() {
           function fetchData() {
               $.ajax({
                   url: 'http://localhost:9091/api/v1/employee/get-all',
                   method: 'GET',
                   success: function(response) {
                       let dataBody = $('#dataBody');
                       dataBody.empty();
                       const data = response.data;
                       console.log("data:", data)
                       $.each(data, function(index, item) {
                           let row = '<tr>' +
                               '<td>' + (index + 1) + '</td>' +
                               '<td>' + item.nik + '</td>' +
                               '<td>' + item.fullName + '</td>' +
                               '<td>' + item.age + '</td>' +
                               '<td>' + item.birth + '</td>' +
                               '<td>' + item.gender + '</td>' +
                               '<td>' + item.address + '</td>' +
                               '<td>' + item.country + '</td>' +
                               '<td>' +
                               '<a href="#" class="text-primary me-2 detail-link" data-nik="' + item.nik + '">Detail</a>' +
                               '<a href="update-data.html?nik=' + item.nik + '" class="text-warning me-2 edit-link">Edit</a>' +
                               '<a href="#" class="text-danger" onclick="deleteEmployee(' + item.nik + ')">Delete</a>' +
                               '</td>' +
                               '</tr>';
                           dataBody.append(row);
                       });
                        $('.detail-link').on('click', function(event) {
                                       event.preventDefault();
                                       let nik = $(this).data('nik');
                                       showDetails(nik);
                                   });
                   },
                   error: function(xhr, status, error) {
                       console.error('Terjadi kesalahan:', error);
                   }
               });
           }

           fetchData();

           function showDetails(nik) {
               $.ajax({
                   url: 'http://localhost:9091/api/v1/employee/' + nik,
                   method: 'GET',
                   success: function(response) {
                       const employee = response.data;
                       console.log("employee:", employee);
                       $('#modalNik').text(employee.nik);
                       $('#modalFullName').text(employee.fullName);
                       $('#modalAge').text(employee.age);
                       $('#modalBirth').text(employee.birth);
                       $('#modalGender').text(employee.gender);
                       $('#modalAddress').text(employee.address);
                       $('#modalCountry').text(employee.country);

                       // Show the modal
                       $('#detailModal').modal('show');
                   },
                   error: function(xhr, status, error) {
                       console.error('Terjadi kesalahan:', error);
                   }
               });
           }

           $('#searchForm').submit(function(event) {
                   event.preventDefault(); // Mencegah form dari reload halaman
                   let nik = $('#nik').val();
                   let name = $('#name').val();

                   // Lakukan AJAX request untuk pencarian
                   $.ajax({
                       url: 'http://localhost:9091/api/v1/employee/search', // Sesuaikan dengan endpoint pencarian Anda
                       method: 'GET',
                       data: { nik: nik, fullName: name }, // Gunakan parameter yang sesuai dengan API Anda
                       success: function(response) {
                           let dataBody = $('#dataBody');
                           dataBody.empty(); // Kosongkan tabel sebelum menampilkan hasil pencarian

                           const data = response.data; // Asumsi respons data berada di response.data
                           if (data && data.length > 0) {
                               $.each(data, function(index, item) {
                                   let row = '<tr>' +
                                       '<td>' + (index + 1) + '</td>' +
                                       '<td>' + item.nik + '</td>' +
                                       '<td>' + item.fullName + '</td>' +
                                       '<td>' + item.age + '</td>' +
                                       '<td>' + item.birth + '</td>' +
                                       '<td>' + item.gender + '</td>' +
                                       '<td>' + item.address + '</td>' +
                                       '<td>' + item.country + '</td>' +
                                       '<td>' +
                                       '<a href="#" class="text-primary me-2">Detail</a>' +
                                       '<a href="update-data.html?nik=' + item.nik + '" class="text-warning me-2 edit-link">Edit</a>' +
                                       '<a href="#" class="text-danger" onclick="deleteEmployee(' + item.nik + ')">Delete</a>' +
                                       '</td>' +
                                       '</tr>';
                                   dataBody.append(row);
                               });
                           } else {
                               dataBody.append('<tr><td colspan="9">Data tidak ditemukan.</td></tr>');
                           }
                       },
                       error: function(xhr, status, error) {
                           console.error('Terjadi kesalahan:', error);
                           $('#dataBody').html('<tr><td colspan="9">Terjadi kesalahan: ' + error + '</td></tr>');
                       }
                   });
               });
            window.deleteEmployee = function(nik) {
            $.ajax({
                url: 'http://localhost:9091/api/v1/employee/' + nik,
                method: 'DELETE',
                success: function(response) {
                    Swal.fire({
                        title: 'Sukses!',
                        text: 'Data berhasil dihapus!',
                        icon: 'success',
                        confirmButtonText: 'OK'
                    }).then(() => {
                        fetchData(); // Refresh data setelah penghapusan
                    });
                },
                error: function(xhr, status, error) {
                    Swal.fire({
                        title: 'Error!',
                        text: 'Terjadi kesalahan: ' + error,
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                }
            });
        }
       });

 $(document).ready(function() {
             // Ambil parameter nik dari URL
             const urlParams = new URLSearchParams(window.location.search);
             const nik = urlParams.get('nik');

             // Ambil data karyawan berdasarkan nik
             $.ajax({
                 url: 'http://localhost:9091/api/v1/employee/' + nik,
                 method: 'GET',
                 success: function(response) {
                     const data = response.data;
                     $('#nik').val(data.nik);
                     $('#fullName').val(data.fullName);
                     $('#age').val(data.age);
                     $('#birth').val(data.birth);
                     $('input[name="gender"][value="' + data.gender + '"]').prop('checked', true);
                     $('#address').val(data.address);
                     $('#country').val(data.country);
                 },
                 error: function(xhr, status, error) {
                     console.error('Terjadi kesalahan:', error);
                 }
             });

             // Saat formulir dikirimkan, lakukan permintaan PATCH
             $('#updateForm').submit(function(event) {
                 event.preventDefault();

                 const updatedData = {
                     nik: $('#nik').val(),
                     fullName: $('#fullName').val(),
                     age: $('#age').val(),
                     birth: $('#birth').val(),
                     gender: $('input[name="gender"]:checked').val(),
                     address: $('#address').val(),
                     country: $('#country').val()
                 };

                 $.ajax({
                     url: 'http://localhost:9091/api/v1/employee/update/' + nik,
                     method: 'PATCH',
                     contentType: 'application/json',
                     data: JSON.stringify(updatedData),
                     success: function(response) {
                         Swal.fire({
                                                 title: 'Sukses!',
                                                 text: 'Data berhasil disimpan!',
                                                 icon: 'success',
                                                 confirmButtonText: 'OK'
                                             }).then((result) => {
                                                 if (result.isConfirmed) {
                                                     window.location.href = 'index.html'; // Redirect ke halaman index.html
                                                 }
                                             });
                     },
                     error: function(xhr, status, error) {
                         Swal.fire({
                                                title: 'Error!',
                                                text: 'Terjadi kesalahan: ' + error,
                                                icon: 'error',
                                                confirmButtonText: 'OK'
                                            });
                     }
                 });
             });
         });

         //Add data employee
         $(document).ready(function() {
                 $('#dataForm').on('submit', function(event) {
                     event.preventDefault(); // Mencegah form dikirim dengan cara biasa
                     const data = {
                         nik: $('#nik').val(),
                         fullName: $('#fullName').val(),
                         gender: $('input[name="gender"]:checked').val(),
                         birth: $('#birth').val(),
                         age: $('#age').val(),
                         address: $('#address').val(),
                         country: $('#country').val()
                     };
                     console.log("data:", data)
                     $.ajax({
                         url: 'http://localhost:9091/api/v1/employee/create', // Ubah URL ini sesuai dengan endpoint server Anda
                         type: 'POST',
                         contentType: 'application/json',
                         data: JSON.stringify(data),
                         success: function(response) {
                             Swal.fire({
                                 title: 'Sukses!',
                                 text: 'Data berhasil disimpan!',
                                 icon: 'success',
                                 confirmButtonText: 'OK'
                             }).then((result) => {
                                 if (result.isConfirmed) {
                                     window.location.href = 'index.html';
                                 }
                             });

                             $('#dataForm')[0].reset();

                         },
                         error: function(xhr, status, error) {
                            Swal.fire({
                                 title: 'Error!',
                                 text: 'NIK sudah digunakan' ,
                                 icon: 'error',
                                 confirmButtonText: 'OK'
                             });
                         }
                     });
                 });
             });
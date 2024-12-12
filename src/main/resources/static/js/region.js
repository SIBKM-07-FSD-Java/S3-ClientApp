// console.log("Region");

$(document).ready(() => {
  $("#tb-region").DataTable({
    ajax: {
      method: "GET",
      url: "/api/region",
      dataSrc: "",
    },
    columnDefs: [{ className: "text-center", targets: "_all" }],
    columns: [
      { data: "id" },
      { data: "name" },
      {
        data: null,
        render: (data) => {
          return /* html */ `
            <div class="d-flex gap-3 justify-content-center align-items-center">
              <button
                type="button"
                class="btn btn-primary btn-sm"
                data-bs-toggle="modal"
                data-bs-target="#detail"
                onclick="findById(${data.id})"
              >
                Detail
              </button>
              <button
                type="button"
                class="btn btn-warning btn-sm"
                data-bs-toggle="modal"
                data-bs-target="#update"
                onclick="beforeUpdate(${data.id})"
              >
                Update
              </button>
              <button
                type="button"
                class="btn btn-danger btn-sm"
                onclick="deleteRegion(${data.id})"
              >
                Delete
              </button>
            </div>
          `;
        },
      },
    ],
  });
});

$("#create-region").click((event) => {
  event.preventDefault();

  const valueName = $("#create-name").val();
  // console.log(valueName);

  if (valueName === "" || valueName === null) {
    Swal.fire({
      position: "center",
      icon: "error",
      title: "Please fill all field!!!",
      showConfirmButton: false,
      timer: 1500,
    });
  } else {
    $.ajax({
      method: "POST",
      url: "/api/region",
      dataType: "JSON",
      beforeSend: addCSRFToken(),
      contentType: "application/json",
      data: JSON.stringify({
        name: valueName,
      }),
      success: (res) => {
        // console.log(res);
        $("#create").modal("hide");
        $("#tb-region").DataTable().ajax.reload();
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Your Region has been saved",
          showConfirmButton: false,
          timer: 1500,
        });
        $("#create-name").val("");
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
});

function findById(id) {
  // console.log(id);

  $.ajax({
    method: "GET",
    url: `/api/region/${id}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      // console.log(res);
      $("#detail-id").val(res.id);
      $("#detail-name").val(res.name);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

function beforeUpdate(id) {
  $.ajax({
    method: "GET",
    url: `/api/region/${id}`,
    dataType: "JSON",
    contentType: "application/json",
    success: (res) => {
      // console.log(res);
      $("#update-id").val(res.id);
      $("#update-name").val(res.name);
    },
    error: (err) => {
      console.log(err);
    },
  });
}

$("#update-region").click((event) => {
  event.preventDefault();

  const valueName = $("#update-name").val();
  const valueId = $("#update-id").val();
  const updateData = {
    name: valueName,
  };
  // console.log(valueName);

  if (valueName === "" || valueName === null) {
    Swal.fire({
      position: "center",
      icon: "error",
      title: "Please fill all field!!!",
      showConfirmButton: false,
      timer: 1500,
    });
  } else {
    $.ajax({
      method: "PUT",
      url: `/api/region/${valueId}`,
      dataType: "JSON",
      beforeSend: addCSRFToken(),
      contentType: "application/json",
      data: JSON.stringify(updateData),
      success: (res) => {
        // console.log(res);
        $("#update").modal("hide");
        $("#tb-region").DataTable().ajax.reload();
        Swal.fire({
          position: "center",
          icon: "success",
          title: "Your Region has been saved",
          showConfirmButton: false,
          timer: 1500,
        });
        $("#update-name").val("");
        $("#update-id").val("");
      },
      error: (err) => {
        console.log(err);
      },
    });
  }
});

function deleteRegion(id) {
  // console.log(id);

  const swalWithBootstrapButtons = Swal.mixin({
    customClass: {
      confirmButton: "btn btn-success btn-sm ms-3",
      cancelButton: "btn btn-danger btn-sm",
    },
    buttonsStyling: false,
  });
  swalWithBootstrapButtons
    .fire({
      title: "Are you sure?",
      text: "You won't be able to delete this!",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "Yes, delete it!",
      cancelButtonText: "No, cancel!",
      reverseButtons: true,
    })
    .then((result) => {
      if (result.isConfirmed) {
        $.ajax({
          method: "DELETE",
          url: `/api/region/${id}`,
          dataType: "JSON",
          beforeSend: addCSRFToken(),
          contentType: "application/json",
          success: (res) => {
            // console.log(res);
            $("#tb-region").DataTable().ajax.reload();
          },
          error: (err) => {
            console.log(err);
          },
        });
        swalWithBootstrapButtons.fire({
          title: "Deleted!",
          text: "Your Region has been deleted.",
          icon: "success",
        });
      } else if (
        /* Read more about handling dismissals below */
        result.dismiss === Swal.DismissReason.cancel
      ) {
        swalWithBootstrapButtons.fire({
          title: "Cancelled",
          text: "Your Region is safe :)",
          icon: "error",
        });
      }
    });
}

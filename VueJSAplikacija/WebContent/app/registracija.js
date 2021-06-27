Vue.component("registracija", {
  data: function () {
    return {
      form: {
        username: "",
        password: "",
        name: "",
        surname: "",
        gender: "",
        birthDate: "",
        userType: { type: "bronze", discount: "", requiredPoints: "" },
        collectedPoints: 0,
        role: "user",
      },
      pol: ["M", "F"],
      odgovor: "",
    };
  },
  template: `
    <div>
        <link rel="stylesheet" href="css/home.css" type="text/css">
        <default-nav></default-nav>

      <b-card id="page_content">
        <b-form @submit="onSubmit" @reset="onReset">
       <b-form-group id="input-group-1" label="Korisnicko ime:" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="form.username"
          placeholder="Unesite korisnicko ime"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group id="input-group-2" label="Lozinka:" label-for="input-2">
        <b-form-input
          id="input-2"
          type="password"
          v-model="form.password"
          placeholder="Unesite lozinku"
          required
        ></b-form-input>
      </b-form-group>
      <b-form-group id="input-group-3" label="Ime:" label-for="input-3">
        <b-form-input
          id="input-3"
          v-model="form.name"
          placeholder="Unesite ime"
          required
        ></b-form-input>
      </b-form-group>

      <b-form-group id="input-group-4" label="Prezime:" label-for="input-4">
        <b-form-input
          id="input-4"
          v-model="form.surname"
          placeholder="Unesite prezime"
          required
        ></b-form-input>
        </b-form-group>

      <b-form-group id="input-group-3" label="Pol:" label-for="input-3">
        <b-form-select
          id="input-3"
          placeholder="Izaberite pol"
          v-model="form.gender"
          :options="pol"
          required
        ></b-form-select>
      </b-form-group>


        <b-form-group id="input-group-5" label="Datum rodjenja:" label-for="input-5">
        <input type = "date" id="input-5" v-model="form.birthDate" required></input>
        </b-form-group>
    
         <b-button type="submit" variant="primary">Registruj se</b-button>
        </b-form>
      
      </b-card>
      
      </div>
    `,

  methods: {
    onSubmit() {
      this.form.birthDate = this.form.birthDate + "T22:00:00.000Z";
      axios
        .post(`rest/user/registerUser`, this.form)
        .then((response) => {
          this.$router.push("/login");
        })
        .catch((error) => {
          console.log("Greska.");
          alert("Postoji korisnik sa unetim korisnickim imenom");
        });
    },
    onReset() {},
  },
});

Vue.component("edit-profil", {
  data: function () {
    return {
      cookie: "",
      role: "",
      form: {},
      pol: ["M", "F"],
    };
  },
  mounted() {
    this.cookie = localStorage.getItem("cookie");
    this.role = localStorage.getItem("role");
    console.log(this.role);
    this.loadUserInfo();
  },
  template: `
    
        <div>
        <link rel="stylesheet" href="css/home.css" type="text/css">

        <div v-if="role  === 'admin'">
          <admin-nav></admin-nav>
        </div>
        <div v-if="role  === 'worker'">
          <worker-nav></worker-nav>
        </div>
        <div v-if="role  === 'user'">
          <korisnik-nav></korisnik-nav>
        </div>
        <div v-if="role  === ''">
          <defaul-nav></defaul-nav>
        </div>
        <b-card id="page_content">
        <b-form @submit="onSubmit" @reset="onReset">
       <b-form-group id="input-group-1" label="Korisnicko ime:" label-for="input-1">
        <b-form-input
          id="input-1"
          v-model="form.username"
          placeholder="Unesite korisnicko ime"
          readonly
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
    
         <b-button type="submit" variant="primary">Sacuvaj podatke</b-button>
        </b-form>
      
      </b-card>
        
        
        </div>
    
    `,
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },

    loadUserInfo() {
      var url = "rest/user/userInfo/" + this.cookie;
      axios.get(url).then((response) => {
        this.form = response.data;
        this.form.birthDate = this.form.birthDate.split("T")[0];
      });
    },
    onSubmit() {
      this.form.birthDate = this.form.birthDate + "T22:00:00.000Z";
      axios
        .put(`rest/user/updateUser`, this.form)
        .then((response) => {
          this.form.birthDate = this.form.birthDate.split("T")[0];
        })
        .catch((error) => {
          console.log("Greska.");
          alert("Postoji korisnik sa unetim korisnickim imenom");
        });
    },
    onReset() {},
  },
});

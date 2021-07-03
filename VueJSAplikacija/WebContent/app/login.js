Vue.component("login", {
  data: function () {
    return {
      form: {
        username: "",
        password: "",
      },
      blocked: false,
      deleted: false,
    };
  },
  template: `
  <div>
        <default-nav></default-nav>

<b-alert style="text-align: center;" v-model="blocked" variant="danger"> Nalog je blokiran</b-alert>
<b-alert style="text-align: center;" v-model="deleted" variant="danger"> Nalog je obrisan</b-alert>
      <b-card id="page_content">
        <b-form @submit="onSubmit" >
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
        <b-button type="submit" variant="primary">Uloguj se</b-button>
        </b-form>
        </b-card>
  
  </div>
     `,
  methods: {
    onSubmit() {
      axios
        .get(`rest/user/testblokiran/name=${this.form.username}`)
        .then((response) => {
          console.log(response.data);
          if (response.data === "blokiran") {
            this.blocked = true;
          } else {
            this.blocked = false;
            this.checkDeleted();
          }
        });
    },
    checkDeleted() {
      axios
        .get(`rest/user/testDeleted/name=${this.form.username}`)
        .then((response) => {
          console.log(response.data);
          if (response.data === "deleted") {
            this.deleted = true;
          } else {
            this.deleted = false;
            this.login();
          }
        });
    },
    login() {
      axios
        .get(`rest/user/loginUser/${this.form.username}/${this.form.password}`)
        .then((response) => {
          console.log(response.data);
          localStorage.setItem("cookie", response.data.split(".")[0]);
          this.role = response.data.split(".")[1];
          localStorage.setItem("role", this.role);
          this.$router.push("/");
        })
        .catch((error) => {
          console.log("Greska.");
          alert("Uneti nevalidni ili nepostojeći parametri, pokušajte ponovo.");
        });
    },
  },
});

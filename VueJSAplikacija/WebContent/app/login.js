Vue.component("login", {
  data: function () {
    return {
      form: {
        username: "",
        password: "",
      },
      blocked: false,
    };
  },
  template: `
  <div>
        <default-nav></default-nav>

<b-alert style="text-align: center;" v-model="blocked" variant="danger"> Nalog je blokiran, odblokirace se prvog u mesecu! </b-alert>

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
          if(response.data == "blokiran") {
          	this.blocked = true;
          }
          else{
          this.blocked = false;
          }
        })
    if(this.blocked != true){
      axios
        .get(`rest/user/loginUser/${this.form.username}/${this.form.password}`)
        .then((response) => {
          console.log(response.data);
          localStorage.setItem("cookie", response.data.split(".")[0]);
          this.role = response.data.split(".")[1];
          localStorage.setItem("role", this.role);
          this.$router.push("/");
          onSubmit();
        })
        .catch((error) => {
          console.log("Greska.");
          alert("Uneti nevalidni ili nepostojeći parametri, pokušajte ponovo.");
        });
     }
    },
  },
});

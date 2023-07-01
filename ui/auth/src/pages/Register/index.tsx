import React, { useState } from "react";
import { useForm, SubmitHandler } from "react-hook-form";
import { UserRegister } from "@model";
import * as yup from "yup";
import { yupResolver } from "@hookform/resolvers/yup";
import { Link } from "react-router-dom";
import { AuthAPI } from "api-estore/dist";
import { SuccessAlert, ErrorAlert } from "common/Alert";

const validatorSchema: yup.ObjectSchema<UserRegister> = yup
  .object({
    username: yup.string().required("Username is required"),
    password: yup
      .string()
      .matches(
        /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,30}/,
        "Minimum 8 and maximum 30 characters, at least one uppercase letter, one lowercase letter, one number and one special character"
      )
      .required("Password is required"),
    rePassword: yup
      .string()
      .oneOf([yup.ref("password")], "Repeat password doesn't match")
      .required("Repeat password is required"),
    email: yup.string().email("Email not valid").required("Email is required"),
  })
  .required();

const Register = () => {
  const [approve, setApprove] = useState(true);
  const [isRegister, setIsRegister] = useState(false);
  const [beError, setBeError] = useState("");

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<UserRegister>({ resolver: yupResolver(validatorSchema) });

  const onSubmitHandler: SubmitHandler<UserRegister> = (data) => {
    const body = {
      username: data.username,
      password: data.password,
      rePassword: data.rePassword,
      role: 1,
      email: data.email,
    };
    AuthAPI.register(body)
      .then((res) => {
        if (res.status == 201) {
          setIsRegister(true);
        }
      })
      .catch((err) => {
        setBeError(err.response.data.message);
      });
  };

  const RegisterForm = () => {
    return (
      <div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0 dark:bg-gray-800 dark:border-gray-700">
        <div className="p-6 space-y-4 md:space-y-6 sm:p-8">
          <h1 className="text-xl font-bold leading-tight tracking-tight text-gray-900 md:text-2xl dark:text-white">
            Create and account
          </h1>
          <form
            className="space-y-4 md:space-y-6"
            onSubmit={handleSubmit(onSubmitHandler)}
          >
            <div>
              <label
                htmlFor="email"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Email
              </label>
              <input
                type="email"
                {...register("email")}
                id="email"
                className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="name@company.com"
              />
              {errors.email && (
                <p className="text-xs text-red-600 dark:text-white">
                  {errors.email.message}
                </p>
              )}
            </div>
            <div>
              <label
                htmlFor="username"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Username
              </label>
              <input
                type="username"
                id="username"
                {...register("username")}
                className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="Enter username"
              />
              {errors.username && (
                <p className="text-xs text-red-600 dark:text-white">
                  {errors.username.message}
                </p>
              )}
            </div>
            <div>
              <label
                htmlFor="password"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Password
              </label>
              <input
                type="password"
                {...register("password")}
                id="password"
                placeholder="••••••••"
                className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
              {errors.password && (
                <p className="text-xs text-red-600 dark:text-white">
                  {errors.password.message}
                </p>
              )}
            </div>
            <div>
              <label
                htmlFor="confirm-password"
                className="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Confirm password
              </label>
              <input
                type="password"
                {...register("rePassword")}
                id="confirm-password"
                placeholder="••••••••"
                className="bg-gray-50 border border-gray-300 text-gray-900 sm:text-sm rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
              {errors.rePassword && (
                <p className="text-xs text-red-600 dark:text-white">
                  {errors.rePassword.message}
                </p>
              )}
            </div>
            <div className="flex items-start">
              <div className="flex items-center h-5">
                <input
                  id="terms"
                  aria-describedby="terms"
                  type="checkbox"
                  checked={!approve}
                  onChange={() => setApprove(!approve)}
                  className="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-primary-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-primary-600 dark:ring-offset-gray-800"
                />
              </div>
              <div className="ml-3 text-sm">
                <label
                  htmlFor="terms"
                  className="font-light text-gray-500 dark:text-gray-300"
                >
                  I accept the{" "}
                  <a
                    className="font-medium text-primary-600 hover:underline dark:text-primary-500"
                    href="#"
                  >
                    Terms and Conditions
                  </a>
                </label>
              </div>
            </div>
            <button
              type="submit"
              className={
                approve
                  ? `w-full text-white focus:ring-4  bg-primary-300 hover:bg-primary-300 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800`
                  : `w-full text-white focus:ring-4  bg-primary-600 hover:bg-primary-700 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800`
              }
              disabled={approve}
            >
              Create an account
            </button>
            <p className="text-sm font-light text-gray-500 dark:text-gray-400">
              Already have an account?{" "}
              <Link
                to="/auth/login"
                className="font-medium text-primary-600 hover:underline dark:text-primary-500"
              >
                Login here
              </Link>
            </p>
          </form>
        </div>
      </div>
    );
  };

  return (
    <div className="bg-gray-50 dark:bg-gray-900">
      <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
        <a
          href="#"
          className="flex items-center mb-6 text-2xl font-semibold text-gray-900 dark:text-white"
        >
          <img
            className="w-8 h-8 mr-2"
            src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/logo.svg"
            alt="logo"
          />
          eStore
        </a>
        <ErrorAlert msg={beError} />
        {isRegister ? (
          <SuccessAlert msg="Register success! Please check your email to active account! Thanks!" />
        ) : (
          <RegisterForm />
        )}
      </div>
    </div>
  );
};

export default Register;
